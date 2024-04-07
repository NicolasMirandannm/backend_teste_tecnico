package backend.infra.rest;

import backend.application.userServices.creation.UserCreationService;
import backend.application.userServices.delete.UserDeleteService;
import backend.application.userServices.dto.UserDto;
import backend.application.userServices.search.UserSearchService;
import backend.application.userServices.update.UserUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserRestController {

  private final UserSearchService userSearchService;
  private final UserCreationService userCreationService;
  private final UserUpdateService userUpdateService;
  private final UserDeleteService userDeleteService;

  @Cacheable(value = "userById")
  @GetMapping("/{id}")
  public ResponseEntity<UserDto> findById(@PathVariable(value = "id") String id) {
    return ResponseEntity.ok(userSearchService.searchById(id));
  }

  @Cacheable(value = "userByName")
  @GetMapping
  public ResponseEntity<List<UserDto>> findByName(@RequestParam("name") String name) {
    return ResponseEntity.ok(userSearchService.searchByName(name));
  }

  @PostMapping
  public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {
    return ResponseEntity.status(201).body(userCreationService.perform(userDto));
  }

  @Async
  @PutMapping
  public void update(@RequestBody UserDto userDto) {
    userUpdateService.perform(userDto);
  }

  @Async
  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable("id") String id) {
    userDeleteService.perform(id);
  }
}
