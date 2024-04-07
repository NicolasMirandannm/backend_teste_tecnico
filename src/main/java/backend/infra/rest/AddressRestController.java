package backend.infra.rest;

import backend.application.addressServices.AddressAbstractService;
import backend.application.addressServices.dto.AddressDto;
import backend.application.addressServices.search.AddressSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("address")
@RequiredArgsConstructor
public class AddressRestController {

  private final AddressSearchService addressSearchService;
  private final AddressAbstractService<AddressDto, AddressDto> addressCreationService;
  private final AddressAbstractService<AddressDto, Void> addressUpdateService;
  private final AddressAbstractService<String, Void> addressDeleteService;

  @Cacheable(value = "addressesByUser")
  @GetMapping("/{userId}")
  public ResponseEntity<List<AddressDto>> findAll(@PathVariable("userId") String userId) {
    return ResponseEntity.ok(addressSearchService.searchAllBy(userId));
  }

  @PostMapping("/{userId}")
  public ResponseEntity<AddressDto> create(@PathVariable("userId") String userId, @RequestBody AddressDto addressDto) {
    return ResponseEntity.status(201).body(addressCreationService.perform(userId, addressDto));
  }

  @Async
  @PutMapping("/{userId}")
  public void update(@PathVariable("userId") String userId, @RequestBody AddressDto addressDto) {
    addressUpdateService.perform(userId, addressDto);
  }

  @Async
  @DeleteMapping("/{userId}/{addressId}")
  public void delete(@PathVariable("userId") String userId, @PathVariable("addressId") String addressId) {
    addressDeleteService.perform(userId, addressId);
  }

}
