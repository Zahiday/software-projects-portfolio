import { useState } from 'react';
import { useAuth } from '../context/AuthContext.jsx';
import { Button } from '../components/Button.jsx';
import { Card } from '../components/Card.jsx';

export function LoginPage() {
  const { login, register, loading } = useAuth();
  const [mode, setMode] = useState('login');
  const [form, setForm] = useState({ name: 'Mo', email: 'mo@example.com', password: 'password123' });
  const [error, setError] = useState('');

  function update(field, value) {
    setForm((current) => ({ ...current, [field]: value }));
  }

  async function submit(event) {
    event.preventDefault();
    setError('');
    try {
      if (mode === 'login') await login(form.email, form.password);
      else await register(form.name, form.email, form.password);
    } catch (err) {
      setError(err.response?.data?.message || 'Aktion fehlgeschlagen');
    }
  }

  return (
    <main className="auth-layout">
      <Card className="auth-card">
        <div className="logo">MPM</div>
        <h1>Mein Projekt Manager</h1>
        <p className="muted">Portfolio-App für Projekte, Aufgaben und Statusübersicht.</p>
        <form onSubmit={submit} className="form">
          {mode === 'register' && (
            <label>Name<input value={form.name} onChange={(e) => update('name', e.target.value)} /></label>
          )}
          <label>E-Mail<input value={form.email} onChange={(e) => update('email', e.target.value)} /></label>
          <label>Passwort<input type="password" value={form.password} onChange={(e) => update('password', e.target.value)} /></label>
          {error && <p className="error">{error}</p>}
          <Button disabled={loading}>{mode === 'login' ? 'Einloggen' : 'Registrieren'}</Button>
        </form>
        <button className="link-button" onClick={() => setMode(mode === 'login' ? 'register' : 'login')}>
          {mode === 'login' ? 'Noch kein Konto? Registrieren' : 'Schon registriert? Einloggen'}
        </button>
      </Card>
    </main>
  );
}
