import { useAuth } from './context/AuthContext.jsx';
import { DashboardPage } from './pages/DashboardPage.jsx';
import { LoginPage } from './pages/LoginPage.jsx';

export function App() {
  const { user } = useAuth();
  return user ? <DashboardPage /> : <LoginPage />;
}
