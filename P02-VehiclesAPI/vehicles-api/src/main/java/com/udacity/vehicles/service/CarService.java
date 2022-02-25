package com.udacity.vehicles.service;

import com.udacity.vehicles.client.maps.Address;
import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.client.prices.Price;
import com.udacity.vehicles.client.prices.PriceClient;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Implements the car service create, read, update or delete
 * information about vehicles, as well as gather related
 * location and price data when desired.
 */
@Service
public class CarService {

    private final CarRepository repository;
    private final WebClient webClientMaps;
    private final WebClient webClientPricing;
    private final Logger logger= LoggerFactory.getLogger(CarService.class);

    @Autowired
    MapsClient mapsClient;

    @Autowired
    PriceClient priceClient;

    public CarService(CarRepository repository, @Qualifier("maps") WebClient webClientMaps, @Qualifier("pricing") WebClient webClientPricing) {
        /**
         * TODO: Add the Maps and Pricing Web Clients you create
         *   in `VehiclesApiApplication` as arguments and set them here.
         *
         *   1. Inject bean by name:
         *      [Spring - Resolving ambiguity by using @Qualifier](https://www.logicbig.com/tutorials/spring-framework/spring-core/inject-bean-by-name.html)
         */
        this.repository = repository;
        this.webClientMaps = webClientMaps;
        this.webClientPricing = webClientPricing;
    }

    /**
     * Gathers a list of all vehicles
     * @return a list of all vehicles in the CarRepository
     */
    public List<Car> list() {
        return repository.findAll();
    }

    /**
     * Gets car information by ID (or throws exception if non-existent)
     * @param id the ID number of the car to gather information on
     * @return the requested car's information, including location and price
     */
    public Car findById(Long id) {
        /**
         * TODO: Find the car by ID from the `repository` if it exists.
         *   If it does not exist, throw a CarNotFoundException
         *   Remove the below code as part of your implementation.
         *
         *   reference:
         *   [Throw Exception in Optional in Java 8 | Baeldung](https://www.baeldung.com/java-optional-throw-exception)
         */
        Car car = repository.findById(id)
                .orElseThrow(CarNotFoundException::new);

        /**
         * TODO: Use the Pricing Web client you create in `VehiclesApiApplication`
         *   to get the price based on the `id` input'
         * TODO: Set the price of the car
         * Note: The car class file uses @transient, meaning you will need to call
         *   the pricing service each time to get the price.
         *
         * @transient
         *   在实际开发过程中，我们常常会遇到这样的问题，这个类的有些属性需要序列化，而其他属性不需要被序列化，打个比方，如果一个用户有一些敏感信息（如密码，银行卡号等），为了安全起见，不希望在网络操作（主要涉及到序列化操作，本地序列化缓存也适用）中被传输，这些信息对应的变量就可以加上transient关键字。换句话说，这个字段的生命周期仅存于调用者的内存中而不会写到磁盘里持久化。
         *
         * 前置知识：
         *  1. 响应式编程，Publisher, subscriber, Mono, Flux
         *      [Java反应式框架Reactor中的Mono和Flux - SegmentFault 思否](https://segmentfault.com/a/1190000024499748###)
         *  2. webclient 以及参数传递：
         *  WebClient是一个接口，执行web请求的主要入口点。
         *      * [Spring Boot WebClient Cheat Sheet | by Stanislav Vain | The Startup | Medium](https://medium.com/swlh/spring-boot-webclient-cheat-sheet-5be26cfa3e)
         *      * [Spring5 Webflux之Webclient使用 - 简书](https://www.jianshu.com/p/15d0a2bed6da)
         *      [Spring 5 WebClient 详细使用教程_kerongao的博客-CSDN博客_spring5 webclient](https://blog.csdn.net/kerongao/article/details/109746190)
         *      [SpringBoot - 网络请求客户端WebClient使用详解2（GET请求）](https://www.hangge.com/blog/cache/detail_2641.html)
         *      [SpringBoot - 网络请求客户端WebClient使用详解3（POST请求）](https://www.hangge.com/blog/cache/detail_2643.html)
         *
         */
//        Price price = webClientPricing.method(HttpMethod.GET)
//                .uri("/services/price?vehicleId={}", car.getId())
//                .accept(MediaType.APPLICATION_JSON)
//                .retrieve()//retrieve()方法是获取响应主体并对其进行解码的最简单方法
//                .bodyToMono(Price.class)// convert the result to Mono
//                .doOnError(error -> logger.error("An error has occurred {}", error.getMessage()))// doOnError() triggers when the Mono completes with an error.
//                .block();
//        String carPrice = String.join(price.getCurrency(), " ", String.valueOf(price.getPrice()));
//        logger.debug("carPrice: " + carPrice);
        /**
         * or we can call priceClient to get the car price
         */
        String carPrice = priceClient.getPrice(car.getId());
        car.setPrice(carPrice);

        /**
         * TODO: Use the Maps Web client you create in `VehiclesApiApplication`
         *   to get the address for the vehicle. You should access the location
         *   from the car object and feed it to the Maps service.
         * TODO: Set the location of the vehicle, including the address information
         * Note: The Location class file also uses @transient for the address,
         * meaning the Maps service needs to be called each time for the address.
         *
         * 前置：
         *      1. JAX-RS：
         *          JAX-RS提供了一些标注将一个资源类，一个POJOJava类，封装为Web资源
         *      2. uri builder:
         *          [使用UriBuilder快速创建URI_最佳 Java 编程-CSDN博客](https://blog.csdn.net/dnc8371/article/details/106699735)
         */
//        Location carLoc = car.getLocation();
//        Address address = webClientMaps
//                .get()
//                .uri(uriBuilder -> uriBuilder
//                        .path("/maps")
//                        .queryParam("lat", carLoc.getLat())
//                        .queryParam("lon", carLoc.getLon())
//                        .build())
//                .accept(MediaType.APPLICATION_JSON)
//                .retrieve()
//                .bodyToMono(Address.class)
//                .doOnError(error -> logger.error("An error has occurred {}", error.getMessage()))
//                .block();
        /**
         * or we can call mapsClient to get the car price
         */
        Location carLocation = mapsClient.getAddress(car.getLocation());
        car.setLocation(carLocation);
        return car;
    }

    /**
     * Either creates or updates a vehicle, based on prior existence of car
     * @param car A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public Car save(Car car) {
        if (car.getId() != null) {
            return repository.findById(car.getId())
                    .map(carToBeUpdated -> {
                        carToBeUpdated.setDetails(car.getDetails());
                        carToBeUpdated.setLocation(car.getLocation());
                        carToBeUpdated.setCondition(car.getCondition());
                        return repository.save(carToBeUpdated);
                    }).orElseThrow(CarNotFoundException::new);
        }

        return repository.save(car);
    }

    /**
     * Deletes a given car by ID
     * @param id the ID number of the car to delete
     */
    public void delete(Long id) {
        /**
         * TODO: Find the car by ID from the `repository` if it exists.
         *   If it does not exist, throw a CarNotFoundException
         */
        Car car = repository.findById(id).orElseThrow(CarNotFoundException::new);

        /**
         * TODO: Delete the car from the repository.
         */
        repository.delete(car);
    }
}
