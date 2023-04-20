package com.demo.app.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.app.models.Task;
import com.demo.app.repositories.TaskRepository;
import com.demo.app.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public Task findById(Long id) {
        Optional<Task> task = this.taskRepository.findById(id);

        return task.orElseThrow(() -> new RuntimeException(
                "Tarefa não encontrada: Id: " + id + ", Tipo: " + Task.class.getName()));
    }

    public List<Task> findAllByUserId(Long userId){
        List<Task> tasks = this.taskRepository.findAllByUserId(userId);

        return tasks;
    }

    @Transactional
    public Task create(Task obj) {
        obj.setId(null);
        obj = this.taskRepository.save(obj);

        return obj;
    }

    @Transactional
    public Task update(Task obj) {
        Task taskNew = findById(obj.getId());
        taskNew.setDescription(obj.getDescription());
        return taskRepository.save(taskNew);
    }

    public void delete(Long id) {
        try {
            this.taskRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não é possível excluir pois há entidades relaciondas.");
        }

    }

}
