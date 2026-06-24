import bcrypt from 'bcryptjs';
import jwt from 'jsonwebtoken';
import { db } from '../models/fileDatabase.js';
import { env } from '../config/env.js';

function createToken(user) {
  return jwt.sign({ userId: user.id }, env.jwtSecret, { expiresIn: '7d' });
}

function publicUser(user) {
  return { id: user.id, name: user.name, email: user.email };
}

export async function register(req, res, next) {
  try {
    const { name, email, password } = req.body;
    const normalizedEmail = email.toLowerCase().trim();
    const existing = db.findOne('users', (user) => user.email === normalizedEmail);
    if (existing) {
      res.status(409);
      throw new Error('E-Mail ist bereits registriert');
    }
    const passwordHash = await bcrypt.hash(password, 10);
    const user = db.insert('users', { name, email: normalizedEmail, passwordHash });
    res.status(201).json({ user: publicUser(user), token: createToken(user) });
  } catch (error) {
    next(error);
  }
}

export async function login(req, res, next) {
  try {
    const { email, password } = req.body;
    const user = db.findOne('users', (item) => item.email === email.toLowerCase().trim());
    if (!user || !(await bcrypt.compare(password, user.passwordHash))) {
      res.status(401);
      throw new Error('E-Mail oder Passwort falsch');
    }
    res.json({ user: publicUser(user), token: createToken(user) });
  } catch (error) {
    next(error);
  }
}

export function me(req, res) {
  res.json({ user: req.user });
}
