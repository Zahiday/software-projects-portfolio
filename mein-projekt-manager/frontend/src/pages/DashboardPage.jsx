import { useState } from 'react';
import { FolderKanban, LogOut, Plus } from 'lucide-react';
import { useAuth } from '../context/AuthContext.jsx';
import { useProjects } from '../hooks/useProjects.js';
import { useTasks } from '../hooks/useTasks.js';
import { Button } from '../components/Button.jsx';
import { Card } from '../components/Card.jsx';
import { TaskCard } from '../components/TaskCard.jsx';

export function DashboardPage() {
  const { user, logout } = useAuth();
  const { projects, createProject, deleteProject } = useProjects();
  const [selectedProjectId, setSelectedProjectId] = useState('');
  const selectedProject = projects.find((project) => project.id === selectedProjectId) || projects[0];
  const activeProjectId = selectedProject?.id || '';
  const { tasks, createTask, updateTask, deleteTask } = useTasks(activeProjectId);
  const [projectForm, setProjectForm] = useState({ name: '', description: '' });
  const [taskForm, setTaskForm] = useState({ title: '', description: '', priority: 'medium', dueDate: '' });

  async function submitProject(event) {
    event.preventDefault();
    if (!projectForm.name.trim()) return;
    await createProject(projectForm);
    setProjectForm({ name: '', description: '' });
  }

  async function submitTask(event) {
    event.preventDefault();
    if (!taskForm.title.trim() || !activeProjectId) return;
    await createTask(taskForm);
    setTaskForm({ title: '', description: '', priority: 'medium', dueDate: '' });
  }

  const columns = [
    ['todo', 'Todo'],
    ['in-progress', 'In Arbeit'],
    ['done', 'Fertig'],
  ];

  return (
    <main className="app-layout">
      <aside className="sidebar">
        <div className="brand"><FolderKanban /> <strong>Projekt Manager</strong></div>
        <p className="muted">Angemeldet als {user.name}</p>
        <form onSubmit={submitProject} className="mini-form">
          <input placeholder="Neues Projekt" value={projectForm.name} onChange={(e) => setProjectForm({ ...projectForm, name: e.target.value })} />
          <textarea placeholder="Beschreibung" value={projectForm.description} onChange={(e) => setProjectForm({ ...projectForm, description: e.target.value })} />
          <Button><Plus size={16} /> Projekt</Button>
        </form>
        <div className="project-list">
          {projects.map((project) => (
            <button key={project.id} className={`project-item ${project.id === activeProjectId ? 'active' : ''}`} onClick={() => setSelectedProjectId(project.id)}>
              <span>{project.name}</span>
              <small>{project.doneCount}/{project.taskCount} Tasks</small>
            </button>
          ))}
        </div>
        <Button variant="ghost" onClick={logout}><LogOut size={16} /> Logout</Button>
      </aside>

      <section className="content">
        <header className="page-header">
          <div>
            <h1>{selectedProject?.name || 'Noch kein Projekt'}</h1>
            <p>{selectedProject?.description || 'Erstelle links dein erstes Projekt.'}</p>
          </div>
          {selectedProject && <Button variant="danger" onClick={() => deleteProject(selectedProject.id)}>Projekt löschen</Button>}
        </header>

        {selectedProject && (
          <Card>
            <h2>Neue Aufgabe</h2>
            <form onSubmit={submitTask} className="task-form">
              <input placeholder="Titel" value={taskForm.title} onChange={(e) => setTaskForm({ ...taskForm, title: e.target.value })} />
              <input placeholder="Beschreibung" value={taskForm.description} onChange={(e) => setTaskForm({ ...taskForm, description: e.target.value })} />
              <select value={taskForm.priority} onChange={(e) => setTaskForm({ ...taskForm, priority: e.target.value })}>
                <option value="low">low</option>
                <option value="medium">medium</option>
                <option value="high">high</option>
              </select>
              <input type="date" value={taskForm.dueDate} onChange={(e) => setTaskForm({ ...taskForm, dueDate: e.target.value })} />
              <Button>Task hinzufügen</Button>
            </form>
          </Card>
        )}

        <div className="board">
          {columns.map(([status, label]) => (
            <Card key={status}>
              <h3>{label}</h3>
              <div className="task-list">
                {tasks.filter((task) => task.status === status).map((task) => (
                  <TaskCard key={task.id} task={task} onStatusChange={(id, nextStatus) => updateTask(id, { status: nextStatus })} onDelete={deleteTask} />
                ))}
              </div>
            </Card>
          ))}
        </div>
      </section>
    </main>
  );
}
