
#### Описание программы
Данные хранятся в базах данных, которые создаются с помощью докера, поэтому для начала запустите докер, после чего файл compose.yaml. В папке db.changelog 4 файла .sql для идентификации таблиц:  
cuisine для хранения блюд,  
users для хранения пользователей,  
orders для хранения заказзов,  
order_dish вспомогательная таблица для установления отношения ManyToMany для заказов и блюд  
db.changelog-master.yaml подключает 4 перечислинных бд.  
Работа пользователя с приложением происходит через RestApi port 8080, удобно тестировать через postman
Программа состоит из 5 пакетов:
### 1)Controller:
#### admin controller
для управления от лица админнистратора  
http://localhost:8080  
@RequestMapping("/admin")
public interface AdminController {

    @PostMapping("/add") пользователь с email добавляет блюдо dish  
    ResponseEntity<Dish> addDish(@RequestBody Dish dish,@RequestParam String email);

    @DeleteMapping("/remove") пользователь с email удаляет блюдо по id   
    ResponseEntity<Void> removeDish(@RequestParam UUID id,@RequestParam String email);

    @PutMapping("/update")  пользователь с email изменяет блюдо по id на блюдо dish  
    ResponseEntity<Dish> updateDish(@RequestBody Dish updatedDish, @RequestParam UUID id,@RequestParam String email);

    @GetMapping("/{id}") пользователь с email получает блюдо по id  
    ResponseEntity<Dish> getDishById(@PathVariable UUID id,@RequestParam String email);

    @GetMapping("/all") пользователь с email получает все блюда
    ResponseEntity<List<Dish>> getAllDishes(@RequestParam String email);
}
#### guest controller

@RequestMapping("/guest")
public interface GuestController {

    @PostMapping("/create") создать заказ order пользователю с email
    ResponseEntity<Order> createOrder(@RequestBody Order order, @RequestParam String email);

    @PostMapping("/cancel") отменить заказ order пользователю с email
    ResponseEntity<String> cancelOrder(@RequestBody Order order, @RequestParam String email);

    @PostMapping("/addDish") добавить dish  в order пользователю с email
    ResponseEntity<String> addDishToOrder(@RequestBody Dish dish, @RequestBody Order order, @RequestParam String email);

    @GetMapping("/status") посмотреть статус order для пользователя email
    ResponseEntity<String> getOrderStatus(@RequestBody Order order, @RequestParam String email);

    @PostMapping("/pay") оплатить заказ order пользователю с email
    ResponseEntity<String> payOrder(@RequestBody Order order, @RequestParam String email);

    @GetMapping("/{id}") получить order по id
    ResponseEntity<Order> getOrderById(@PathVariable UUID id);

    @GetMapping("/user") получить список order по email
    ResponseEntity<List<Order>> getOrdersByUserId(@RequestParam String email);
}
#### user controller
@RequestMapping("/users")
public interface UserController {

    @PostMapping("/register")  зарегистрироваться
    ResponseEntity<?> registerUser(@RequestBody User user);

    @PostMapping("/login") войти
    ResponseEntity<?> loginUser(@RequestBody User user);

    @PostMapping("/logout") выйти
    ResponseEntity<?> logoutUser(@RequestBody User user);
}
### 2)Models
Dish order and user используятся для соответсвующих сущностей.
### 3)repository
Наследуются от JpaRepository
### 4)access
Основана на паттерне цепочка обязанностей, проверяет в сети ли пользователь, зарегистрирован ли он и имеет ли он нужный уровень доступа к вызываемой операции. Основная идея паттерна состоит в том, чтобы избежать привязки отправителя запроса к его получателю, давая нескольким объектам возможность обработать запрос. Эти объекты образуют цепочку.
User irder dish service соответственно для user controller guest controller and admin controller. Kitchen service реализует многопоточный подход для обработки заказов.
### 5)service
User order dish service соответственно для user controller guest controller and admin controller. Kitchen service реализует многопоточный подход для обработки заказов.
