package com.udacity.vehicles.api;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.service.CarService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;

import io.swagger.annotations.*;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implements a REST-based controller for the Vehicles API.
 * restController = @Controller + @ResponseBody
 * @ResponseBody:
 *      当我们想让页面知道我们返回的数据不是按照html标签的页面来解析，而是其他某种格式的数据解析时（如json、xml等）使用。
 *      放在controller层的方法上，将Controller的方法返回的对象，通过适当的HttpMessageConverter转换为指定格式后，写入到Response对象的body数据区。
 */
@ApiResponses(value = {
        @ApiResponse(code=400, message = "This is a bad request"),
        @ApiResponse(code=401, message = "Due to security constraints, your access request cannot be authorized"),
        @ApiResponse(code=500, message = "the server down")
})
@RestController
@Api(value = "Car controller")
@RequestMapping("/cars")
class CarController {

    private final CarService carService;
    private final CarResourceAssembler assembler;

    CarController(CarService carService, CarResourceAssembler assembler) {
        this.carService = carService;
        this.assembler = assembler;
    }

    /**
     * Creates a list to store any vehicles.
     * @return list of vehicles
     *
     * <Class name>::<method name>
     */
    @ApiOperation(value = "Creates a list to store any vehicles.")
    @GetMapping
    Resources<Resource<Car>> list() {
        List<Resource<Car>> resources = carService.list().stream().map(assembler::toResource)
                .collect(Collectors.toList());
        return new Resources<>(resources,
                linkTo(methodOn(CarController.class).list()).withSelfRel());
    }

    /**
     * Gets information of a specific car by ID.
     * @param id the id number of the given vehicle
     * @return all information for the requested vehicle
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "Gets information of a specific car by ID.", notes="need car id")
    @ApiImplicitParam(name = "id", value = "car Id", required = true, dataType = "Long", paramType = "path")
    Resource<Car> get(@PathVariable Long id) {
        /**
         * TODO: Use the `findById` method from the Car Service to get car information.
         * TODO: Use the `assembler` on that car and return the resulting output.
         *   Update the first line as part of the above implementing.
         */
        Car car = carService.findById(id);

        return assembler.toResource(car);
    }

    /**
     * Posts information to create a new vehicle in the system.
     * @param car A new vehicle to add to the system.
     * @return response that the new vehicle was added to the system
     * @throws URISyntaxException if the request contains invalid fields or syntax
     */
    @PostMapping
    @ApiOperation(value = "Posts information to create a new vehicle in the system..")
    @ApiImplicitParam(name = "car", value = "a new car", required = true, dataType = "Car", paramType = "body")
    ResponseEntity<?> post(@Valid @RequestBody Car car) throws URISyntaxException {
        /**
         * TODO: Use the `save` method from the Car Service to save the input car.
         * TODO: Use the `assembler` on that saved car and return as part of the response.
         *   Update the first line as part of the above implementing.
         *
         *   created: Create a new builder with a CREATED status and a location header set to the given URI.
         *      201 Created 是一个代表成功的应答状态码，表示请求已经被成功处理，并且创建了新的资源。新的资源在应答返回之前已经被创建
         */
        carService.save(car);
        Resource<Car> resource = assembler.toResource(car);
        return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
    }

    /**
     * Updates the information of a vehicle in the system.
     * @param id The ID number for which to update vehicle information.
     * @param car The updated information about the related vehicle.
     * @return response that the vehicle was updated in the system
     */
    @PutMapping("/{id}")
    @ApiOperation(value = "Updates the information of a vehicle in the system.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "The ID number for which to update vehicle information."
                                        , required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "car", value = "The updated information about the related vehicle."
                                        , required = true, dataType = "Car", paramType = "body")
    })
    ResponseEntity<?> put(@PathVariable Long id, @Valid @RequestBody Car car) {
        /**
         * TODO: Set the id of the input car object to the `id` input.
         * TODO: Save the car using the `save` method from the Car service
         * TODO: Use the `assembler` on that updated car and return as part of the response.
         *   Update the first line as part of the above implementing.
         */
        car.setId(id);
        Car newCar = carService.save(car);
        Resource<Car> resource = assembler.toResource(newCar);
        return ResponseEntity.ok(resource);
    }

    /**
     * Removes a vehicle from the system.
     * @param id The ID number of the vehicle to remove.
     * @return response that the related vehicle is no longer in the system
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Removes a vehicle from the system.")
    @ApiImplicitParam(name = "id", value = "The ID number of the vehicle to remove."
            , required = true, dataType = "Long", paramType = "path")
    ResponseEntity<?> delete(@PathVariable Long id) {
        /**
         * TODO: Use the Car Service to delete the requested vehicle.
         */
        carService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
