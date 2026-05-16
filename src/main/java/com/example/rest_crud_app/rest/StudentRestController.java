package com.example.rest_crud_app.rest;

import com.example.rest_crud_app.entity.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")

public class StudentRestController {

    private List<Student> theStudents;
    @PostConstruct
    public void loadData() {

        theStudents = new ArrayList<>();

        theStudents.add(new Student("Munteanu","Eugen"));
        theStudents.add(new Student("Ojog", "Maria"));
        theStudents.add(new Student("Gonzales", "Pedro"));

    }
    @GetMapping("/students/{studentId}")
    public Student getStudent(@PathVariable int studentId){


        //verificam din nou studentID si dimensiunea listei
        if ( (studentId >= theStudents.size())  ||  (studentId < 0)) {
            throw new StudentNotFoundExeption("Student id not found - " + studentId);
        }

        return theStudents.get(studentId);
    }



    //definim endpoint-ul pentru /students
    @GetMapping("/students")
    public List<Student> getStudents(){

        return theStudents;
    }

    //exception handler

    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundExeption ex) {

        //create StudentErrorResponse

        StudentErrorResponse error = new StudentErrorResponse();

        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(ex.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        //return ResponseEntity

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(Exception ex) {

        StudentErrorResponse error = new StudentErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage("Propriul nostru text pentru eroare");
        error.setTimeStamp(System.currentTimeMillis());

        //return ResponseEntity

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


}
