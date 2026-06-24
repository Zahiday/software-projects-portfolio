import { Router } from 'express';
import { login, me, register } from '../controllers/authController.js';
import { requireAuth } from '../middlewares/authMiddleware.js';
import { requireFields } from '../middlewares/validate.js';

export const authRoutes = Router();
authRoutes.post('/register', requireFields(['name', 'email', 'password']), register);
authRoutes.post('/login', requireFields(['email', 'password']), login);
authRoutes.get('/me', requireAuth, me);
