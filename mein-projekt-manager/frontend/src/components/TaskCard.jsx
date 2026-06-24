import { Trash2 } from 'lucide-react';

export function TaskCard({ task, onStatusChange, onDelete }) {
  return (
    <div className="task-card">
      <div>
        <h4>{task.title}</h4>
        {task.description && <p>{task.description}</p>}
        <span className={`badge badge-${task.priority}`}>{task.priority}</span>
        {task.dueDate && <span className="date">Fällig: {task.dueDate}</span>}
      </div>
      <div className="task-actions">
        <select value={task.status} onChange={(event) => onStatusChange(task.id, event.target.value)}>
          <option value="todo">Todo</option>
          <option value="in-progress">In Arbeit</option>
          <option value="done">Fertig</option>
        </select>
        <button className="icon-button" onClick={() => onDelete(task.id)} aria-label="Task löschen">
          <Trash2 size={16} />
        </button>
      </div>
    </div>
  );
}
