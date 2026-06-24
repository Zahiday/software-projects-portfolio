import jwt from 'jsonwebtoken';
import { env } from '../config/env.js';
import { db } from '../models/fileDatabase.js';

export function requireAuth(req, res, next) {
  const header = req.headers.authorization || '';
  const token = header.startsWith('Bearer ') ? header.slice(7) : null;

  if (!token) {
    res.status(401);
    return next(new Error('Nicht authentifiziert'));
  }

  try {
    const payload = jwt.verify(token, env.jwtSecret);
    const user = db.findById('users', payload.userId);
    if (!user) {
      res.status(401);
      return next(new Error('Benutzer nicht gefunden'));
    }
    req.user = { id: user.id, name: user.name, email: user.email };
    next();
  } catch {
    res.status(401);
    next(new Error('Token ungültig oder abgelaufen'));
  }
}
