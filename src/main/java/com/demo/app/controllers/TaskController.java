package com.demo.app.controllers;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.demo.app.models.Task;
import com.demo.app.models.User.createUser;
import com.demo.app.services.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/task")
@Validated
public class TaskController {
    
    @Autowired
    private TaskService taskService;

    
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Task> findById(@PathVariable Long id){
        Task taskFind = this.taskService.findById(id);

        return ResponseEntity.ok(taskFind);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Validated(createUser.class)
    public ResponseEntity<Void> create(Task task){
        this.taskService.create(task);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                  .path("/{id}").buildAndExpand(task.getId()).toUri();

        return ResponseEntity.created(uri).build();

    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Validated
    public ResponseEntity<Void> update(@Valid @RequestBody Task task, @PathVariable Long id){
        task.setId(id);
        this.taskService.update(task);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.taskService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Task>> findAllByUserId(@PathVariable Long userId){
        List<Task> tasks = this.taskService.findAllByUserId(userId);

        return ResponseEntity.ok().body(tasks);

    }
}
