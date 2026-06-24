import fs from 'fs';
import path from 'path';
import { fileURLToPath } from 'url';
import { nanoid } from 'nanoid';
import { env } from '../config/env.js';

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);
const root = path.resolve(__dirname, '../../');
const dbPath = path.resolve(root, env.dataFile);

const defaultData = {
  users: [],
  projects: [],
  tasks: [],
};

function ensureDatabase() {
  const dir = path.dirname(dbPath);
  if (!fs.existsSync(dir)) fs.mkdirSync(dir, { recursive: true });
  if (!fs.existsSync(dbPath)) fs.writeFileSync(dbPath, JSON.stringify(defaultData, null, 2));
}

function read() {
  ensureDatabase();
  return JSON.parse(fs.readFileSync(dbPath, 'utf8'));
}

function write(data) {
  ensureDatabase();
  fs.writeFileSync(dbPath, JSON.stringify(data, null, 2));
}

export const db = {
  all(collection) {
    return read()[collection] || [];
  },
  findById(collection, id) {
    return (read()[collection] || []).find((item) => item.id === id) || null;
  },
  findOne(collection, predicate) {
    return (read()[collection] || []).find(predicate) || null;
  },
  insert(collection, value) {
    const data = read();
    const item = {
      id: nanoid(12),
      createdAt: new Date().toISOString(),
      updatedAt: new Date().toISOString(),
      ...value,
    };
    data[collection].push(item);
    write(data);
    return item;
  },
  update(collection, id, patch) {
    const data = read();
    const index = data[collection].findIndex((item) => item.id === id);
    if (index === -1) return null;
    data[collection][index] = {
      ...data[collection][index],
      ...patch,
      updatedAt: new Date().toISOString(),
    };
    write(data);
    return data[collection][index];
  },
  remove(collection, id) {
    const data = read();
    const before = data[collection].length;
    data[collection] = data[collection].filter((item) => item.id !== id);
    write(data);
    return data[collection].length !== before;
  },
  reset() {
    write(defaultData);
  },
};
