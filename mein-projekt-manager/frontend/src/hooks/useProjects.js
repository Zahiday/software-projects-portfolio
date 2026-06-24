import { useCallback, useEffect, useState } from 'react';
import { api } from '../services/api.js';

export function useProjects() {
  const [projects, setProjects] = useState([]);
  const [loading, setLoading] = useState(true);

  const loadProjects = useCallback(async () => {
    setLoading(true);
    try {
      const res = await api.get('/projects');
      setProjects(res.data);
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    loadProjects();
  }, [loadProjects]);

  async function createProject(payload) {
    await api.post('/projects', payload);
    await loadProjects();
  }

  async function deleteProject(id) {
    await api.delete(`/projects/${id}`);
    await loadProjects();
  }

  return { projects, loading, createProject, deleteProject, reload: loadProjects };
}
