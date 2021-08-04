package ru.megafon.test.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.megafon.test.domain.User;
import ru.megafon.test.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Список REST запросов:
 * GET /user/ список всех пользователей.
 * GET /user/{id} - пользователь с id.
 * POST /user/{User} - создает пользователя.
 * PUT /user/{User} - обновляет пользователя.
 * DELETE /user/{id} - удаляет
 *
 * Передача данных сущности User осуществляется через пакет JSON
 *
 * @author Enver Eskendarov
 * @version 1.0 04.08.2021
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository user;

    public UserController(final UserRepository user) {
        this.user = user;
    }

    @GetMapping("/")
    public List<User> findAll() {
        return StreamSupport.stream(
                this.user.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable int id) {
        final Optional<User> user = this.user.findById(id);
        return new ResponseEntity<>(
                user.orElse(new User()),
                user.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/")
    public ResponseEntity<User> create(@RequestBody User user) {
        return new ResponseEntity<>(
                this.user.save(user),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody User user) {
        this.user.save(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        final User user = new User();
        user.setId(id);
        this.user.delete(user);
        return ResponseEntity.ok().build();
    }
}
