package com.example.todocrud.services;

import com.example.todocrud.entity.Todo;
import com.example.todocrud.entity.Users;
import com.example.todocrud.repository.ToDoRepository;
import com.example.todocrud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TodoServices {

    @Autowired
     UserServices userServices;
    @Autowired
    ToDoRepository toDoRepository;
    @Autowired
     UserRepository userRepository;

    public Todo getTodoById(Long todoId){
        // write code
        return toDoRepository.findById(todoId).get();
    }

    public void addTodo(Long userId, Todo todo){
        
    	Users existuser=userServices.getUserById(userId);
    	existuser.getTodoList().add(todo);
    	
    	userRepository.save(existuser);
    }
    public void deleteTodo(Long userId,Long todoId){
    	Users existuser=userServices.getUserById(userId);
    	Todo existtodo=this.getTodoById(todoId);
    	List<Todo> listTodo=existuser.getTodoList();
    	Iterator<Todo> itr=listTodo.iterator();
    	while (itr.hasNext()) {
			Todo todo=(Todo) itr.next();
			if(todo.getId()==todoId) {
				itr.remove();
			}		
		}
    	existuser.setTodoList(listTodo);
    	userRepository.save(existuser);
    	toDoRepository.deleteById(todoId);
    }

    public void toggleTodoCompleted(Long todoId){
        Todo todo = this.getTodoById(todoId);
        todo.setCompleted(!todo.getCompleted());
        
        toDoRepository.save(todo);
    }

    public void updateTodo(Todo todo) {
    	toDoRepository.save(todo);
    	
    }

}
