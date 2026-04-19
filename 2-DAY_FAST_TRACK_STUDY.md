### Structure

```
src/main/java/com/university/eventmanagement/
│
├── EventManagementApplication.java   ← ENTRY POINT (main() method)
│
├── model/                             ← LAYER 1: What data looks like
│   ├── User.java
│   ├── Event.java
│   ├── Booking.java
│   └── Rating.java
│
├── repository/                        ← LAYER 2: How to query database
│   ├── UserRepository.java
│   ├── EventRepository.java
│   ├── BookingRepository.java
│   └── RatingRepository.java
│
├── service/                           ← LAYER 3: Business logic & validation
│   ├── UserService.java
│   ├── EventService.java
│   ├── BookingService.java
│   └── RatingService.java
│
├── controller/                        ← LAYER 4: HTTP routes (URLs)
│   ├── AuthController.java
│   ├── StudentController.java
│   └── AdminController.java
│
├── config/
│   └── WebConfig.java                 ← Configuration & security setup
│
└── interceptor/
    └── AuthInterceptor.java           ← Checks every request for login
```

### Frontend Structuree

```
src/main/resources/templates/          ← HTML files (Thymeleaf)
├── index.html                         ← Homepage
├── auth/
│   ├── login.html                     ← Login page
│   └── register.html                  ← Registration page
├── student/
│   ├── dashboard.html                 ← Student home
│   └── events.html                    ← Event list to book
├── admin/
│   ├── dashboard.html                 ← Admin home
│   └── event-form.html                ← Create event form
└── fragments/
    └── navbar.html                    ← Navigation bar
```

---

## HOURS 3-6: STUDY MODELS (Database Entities)

Models = Java representation of database tables. Each model has properties that become database columns.

### MODEL 1: User.java

```java
@Entity
@Table(name = "users")
public class User {
    @Id
    private Long id;                   // Unique identifier
    private String email;              // Login email
    private String password;           // Login password
    private String role;               // "ADMIN" or "STUDENT"
}
```

**What does it do?**
- Represents a user account (student or admin)
- Spring creates database table: `users` with columns: id, email, password, role

**Why exists?**
- Need to store user information
- Spring needs Java representation of database table

**If deleted:**
- ❌ Can't login/register
- ❌ No way to store user data
- ❌ Entire app breaks

**Questions:**
- ✅ What fields does User have? (id, email, password, role)
- ✅ What's the role field for? (determines ADMIN vs STUDENT)
- ✅ How does User relate to other models? (User can have many Bookings)

---

### MODEL 2: Event.java

```java
@Entity
@Table(name = "events")
public class Event {
    @Id
    private Long id;                           // Unique identifier
    private String title;                      // Event name
    private LocalDateTime date;                // When event happens
    private int totalSeats;                    // Max capacity
    private int availableSeats;                // Seats left to book
    
    @OneToMany(mappedBy = "event")
    private List<Booking> bookings;            // All bookings for this event
}
```

**What does it do?**
- Represents a university event
- Spring creates table: `events` with columns: id, title, date, totalSeats, availableSeats

**Why exists?**
- Need to store event information
- Need to track how many seats available

**If deleted:**
- ❌ Can't create events
- ❌ No event system
- ❌ Students can't book

**Questions:**
- ✅ What's difference between totalSeats and availableSeats? 
  - totalSeats = always 50
  - availableSeats = 50 - (number of bookings)
- ✅ What does @OneToMany mean? (One Event has many Bookings)
- ✅ How many events can one user book? (Many - no limit)

---

### MODEL 3: Booking.java

```java
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    private Long id;                                    // Unique identifier
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;                                  // Which student booked?
    
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;                                // Which event?
    
    private LocalDateTime bookingDate;                  // When booked?
    private String paymentStatus;                       // "PENDING" or "PAID"
}
```

**What does it do?**
- Represents a student's reservation for an event
- Links User to Event: "User #5 booked Event #3"
- Spring creates table: `bookings` with columns: id, user_id, event_id, bookingDate, paymentStatus

**Why exists?**
- Need to track which student booked which event
- Need to store booking information
- This is the CONNECTION between users and events

**If deleted:**
- ❌ Can't track who booked what
- ❌ Can't prevent double-booking
- ❌ Booking system completely broken

**Questions:**
- ✅ What does @ManyToOne mean? (Many Bookings point to One User)
- ✅ Why does Booking have user_id AND event_id? (Connects the two)
- ✅ What's paymentStatus? (Tracks if student paid or not)

---

### MODEL 4: Rating.java

```java
@Entity
@Table(name = "ratings")
public class Rating {
    @Id
    private Long id;                      // Unique identifier
    
    @ManyToOne
    private User user;                    // Which student rated?
    
    @ManyToOne
    private Event event;                  // Which event rated?
    
    private int rating;                   // 1-5 stars
    private String review;                // Text review
}
```

**What does it do?**
- Stores student reviews and ratings for events
- Spring creates table: `ratings` with columns: id, user_id, event_id, rating, review

**Why exists?**
- Students need way to give feedback
- Collect ratings for events

**If deleted:**
- ⚠️ Can't rate events (feature breaks)
- ⚠️ But app still works

**Questions:**
- ✅ Can a user rate same event twice? (No, should be prevented)
- ✅ What's difference between rating and review? (rating = 1-5, review = text)

---

### ✅ DAY 1 CHECKPOINT

Before moving to Day 2, verify:
- [ ] App running on http://localhost:8080
- [ ] MySQL has tables: users, events, bookings, ratings
- [ ] Understand what each model does
- [ ] Know relationships: User ← Booking → Event, User → Rating → Event
- [ ] Can answer: "If User.java deleted, what breaks?"

---

---

# 🎯 DAY 2: BACKEND + FRONTEND + INTEGRATION

## HOUR 1: REPOSITORIES (Database Access Layer)

Repositories = Java interfaces that query the database. Spring auto-generates SQL!

### REPOSITORY 1: UserRepository.java

```java
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);    // SELECT * FROM users WHERE email = ?
    // Spring auto-generates SQL ✅
}
```

**What does it do?**
- Provides methods to find users in database
- Spring auto-generates SQL queries from method names

**Why exists?**
- Separates database queries from business logic
- Keeps code organized in layers

**If deleted:**
- ❌ Can't query database
- ❌ Can't find users
- ❌ Everything breaks

**Common methods:**
```
findById(Long id)         → SELECT * FROM users WHERE id = ?
findByEmail(String email) → SELECT * FROM users WHERE email = ?
save(User user)           → INSERT INTO users ... or UPDATE ...
findAll()                 → SELECT * FROM users
delete(User user)         → DELETE FROM users WHERE id = ?
```

---

### REPOSITORY 2: EventRepository.java

**What does it do?** Query and save events  
**Why exists?** Need to access event data  
**If deleted?** ❌ Can't load events from database

---

### REPOSITORY 3: BookingRepository.java

**What does it do?** Query and save bookings  
**Why exists?** Need to access booking information  
**If deleted?** ❌ Can't track bookings

**Likely methods:**
```
findByUser(User user)      → Get all bookings by this user
findByEvent(Event event)   → Get all bookings for this event
findByUserAndEvent(...)    → Check if user already booked
```

---

## HOUR 1-2: SERVICES (Business Logic Layer)

Services = Where validation and business logic happens. Controllers call Services, Services use Repositories.

### SERVICE 1: UserService.java

```java
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    public User authenticateUser(String email, String password) {
        // 1. Find user by email (uses repository)
        User user = userRepository.findByEmail(email);
        
        // 2. Check password matches
        if (user == null || !user.getPassword().equals(password)) {
            return null;  // Login failed
        }
        
        // 3. Return user (login success)
        return user;
    }
}
```

**What does it do?**
- Handles user registration and login
- Contains validation logic
- Calls Repository to access database

**Why exists?**
- Separates validation from database access
- Controllers shouldn't do validation
- Keep business logic in one place

**If deleted:**
- ❌ No login system
- ❌ No user validation
- ❌ Can't register users

**Key methods:**
- `registerUser()` - Create new account with validation
- `authenticateUser()` - Check if email/password correct

---

### SERVICE 2: EventService.java

**What does it do?** Handle event creation, updates, seat management  
**Why exists?** Business logic for events (validation, seat counting)  
**If deleted?** ❌ Can't create/manage events

**Key methods:**
```
createEvent()    → Validate & create new event
updateEvent()    → Update existing event
getAllEvents()   → Get all events with available seats
decreaseSeats()  → When user books, decrease available_seats
```

---

### SERVICE 3: BookingService.java (MOST IMPORTANT!)

```java
@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private EventRepository eventRepository;
    
    public Booking createBooking(Long userId, Long eventId) {
        // VALIDATION 1: User exists?
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new Exception("User not found"));
        
        // VALIDATION 2: Event exists?
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new Exception("Event not found"));
        
        // VALIDATION 3: Seats available?
        if (event.getAvailableSeats() <= 0) {
            throw new Exception("No seats available");
        }
        
        // VALIDATION 4: User not already booked?
        Booking existing = bookingRepository.findByUserAndEvent(user, event);
        if (existing != null) {
            throw new Exception("Already booked this event");
        }
        
        // CREATE & SAVE
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setEvent(event);
        booking.setBookingDate(LocalDateTime.now());
        booking.setPaymentStatus("PENDING");
        
        bookingRepository.save(booking);  // Save to database
        
        // UPDATE AVAILABLE SEATS
        event.setAvailableSeats(event.getAvailableSeats() - 1);
        eventRepository.save(event);  // Update database
        
        return booking;
    }
}
```

**What does it do?**
- Handles booking creation with complex validation
- Prevents double-booking, checks seats, validates users
- Updates database when booking created

**Why exists?**
- Booking has business rules that must be validated
- Can't allow invalid bookings

**If deleted:**
- ❌ Anyone could book without validation
- ❌ Double-booking possible
- ❌ Overbooking possible (more bookings than seats)

---

## HOUR 3: CONTROLLERS (HTTP Routes)

Controllers = Handle HTTP requests from browser. Map URLs to Java methods.

### CONTROLLER 1: AuthController.java

```java
@Controller
public class AuthController {
    @Autowired
    private UserService userService;
    
    @GetMapping("/login")
    public String loginForm() {
        // Browser navigates to /login
        // Show login.html template
        return "auth/login";
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String email, 
                       @RequestParam String password,
                       HttpSession session) {
        // Browser submits form to /login
        // Call service to validate
        User user = userService.authenticateUser(email, password);
        
        if (user != null) {
            // Login success: store user in session
            session.setAttribute("user", user);
            return "redirect:/student/dashboard";
        } else {
            // Login failed: show form again with error
            return "auth/login";
        }
    }
}
```

**What does it do?**
- Handle /login and /register URL requests
- Route requests to appropriate methods
- Return HTML templates or redirects

**Why exists?**
- Spring needs HTTP endpoints for web requests
- Map URLs to Java code

**If deleted:**
- ❌ No way to login
- ❌ Can't access app

**Routes:**
```
GET  /login         → Show login form
POST /login         → Process login
GET  /register      → Show registration form
POST /register      → Create new user
```

---

### CONTROLLER 2: StudentController.java

```java
@GetMapping("/student/events")
public String getEvents(Model model) {
    // Get all events from service
    List<Event> events = eventService.getAllEvents();
    
    // Add to Model (sends to template)
    model.addAttribute("events", events);
    
    // Return template name
    return "student/events";
}

@PostMapping("/student/book")
public String bookEvent(@RequestParam Long eventId,
                       HttpSession session) {
    // Get logged-in user from session
    User user = (User) session.getAttribute("user");
    
    // Create booking
    bookingService.createBooking(user.getId(), eventId);
    
    // Redirect to success page
    return "redirect:/student/my-bookings";
}
```

**What does it do?**
- Handle all /student/* URLs
- Get user events, book events, view bookings

**Why exists?**
- Need endpoints for student features

**If deleted:**
- ❌ Students can't view/book events

**Routes:**
```
GET  /student/dashboard   → Student home
GET  /student/events      → List events
POST /student/book        → Book event
```

---

### CONTROLLER 3: AdminController.java

**What does it do?** Handle /admin/* URLs  
**Why exists?** Need endpoints for admin features  
**If deleted?** ❌ Admins can't manage events

**Routes:**
```
GET  /admin/dashboard     → Admin home
GET  /admin/events        → Manage events
POST /admin/create-event  → Create new event
```

---

## HOUR 4: TEMPLATES (Frontend - HTML)

Templates = HTML files that Thymeleaf (server) fills with data, then sends to browser.

### TEMPLATE 1: login.html

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<body>
    <h1>Login</h1>
    
    <!-- Form submits to /login POST -->
    <form th:action="@{/login}" method="post">
        <input type="email" name="email" />
        <input type="password" name="password" />
        <button type="submit">Login</button>
    </form>
</body>
</html>
```

**What does it do?** Show login form to user  
**Why exists?** Users need HTML interface to enter email/password  
**If deleted?** ⚠️ Users can't see form (but API still works)

**Thymeleaf features:**
```
th:action="@{/path}"     → Form submits to /path
th:each="item : ${items}" → Loop through list
th:if="${condition}"     → Show if true
${variable}              → Show variable value
```

---

### TEMPLATE 2: events.html

```html
<table>
    <!-- Loop through events from Controller -->
    <tr th:each="event : ${events}">
        <td th:text="${event.title}"></td>
        <td th:text="${event.availableSeats}"></td>
        <td>
            <!-- Form to book event -->
            <form th:action="@{/student/book}" method="post">
                <input type="hidden" name="eventId" th:value="${event.id}" />
                <button type="submit">Book</button>
            </form>
        </td>
    </tr>
</table>
```

**What does it do?** Display events list for booking  
**Why exists?** Students need UI to see events  
**If deleted?** ❌ Students can't see events

---

### TEMPLATE 3: navbar.html

**What does it do?** Reusable navigation menu  
**Why exists?** Keep navigation consistent across pages  
**If deleted?** ⚠️ No navigation (confusing UX)

---

## HOUR 5: SECURITY & INTERCEPTOR

### AuthInterceptor.java

```java
@Component
public class AuthInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        String url = request.getRequestURI();
        
        // 1. Public routes (no login needed)
        if (url.equals("/login") || url.equals("/")) {
            return true;  // Allow access
        }
        
        // 2. Check if user in session
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("/login");  // Redirect to login
            return false;  // Block request
        }
        
        // 3. Block non-admins from /admin routes
        if (url.startsWith("/admin") && !"ADMIN".equals(user.getRole())) {
            return false;  // Block non-admin
        }
        
        return true;  // Allow access
    }
}
```

**What does it do?**
- Intercepts EVERY request
- Checks if user logged in
- Prevents non-admins from /admin pages

**Why exists?**
- Prevent unauthorized access
- Security layer

**If deleted:**
- ❌ Anyone can access /admin
- ❌ Can access without login
- ❌ Security broken

---

## HOUR 6: COMPLETE END-TO-END FLOW

### Flow 1: User Registration

```
1. User clicks Register → GET /register
   → AuthController.registerForm()
   → Shows register.html template

2. User fills form, submits → POST /register
   → AuthController.register()
   → Calls UserService.createUser()
   → Service validates (email unique? password strong?)
   → UserRepository.save()
   → MySQL INSERT: INSERT INTO users (email, password, role)
   → New user stored in database ✅
   → Redirect to /login
```

---

### Flow 2: User Login + Session

```
1. User goes to /login
   → AuthController.loginForm()
   → Shows login.html

2. User submits email/password → POST /login
   → AuthController.login()
   → Calls UserService.authenticateUser()
   → Service queries: UserRepository.findByEmail(email)
   → Checks: password matches?
   → If yes: session.setAttribute("user", user)
   → Session stored on server, cookie sent to browser
   → Redirect to /student/dashboard ✅

3. User clicks anything → Interceptor runs
   → Checks: User in session?
   → Yes → Allow access
   → No → Redirect to /login
```

---

### Flow 3: Student Booking Event

```
1. Student logged in, views events → GET /student/events
   → StudentController.getEvents()
   → EventService.getAllEvents()
   → EventRepository.findAll()
   → MySQL: SELECT * FROM events
   → Returns list, shows in template

2. Student clicks "Book" button → POST /student/book
   → StudentController.bookEvent()
   → Gets user from session
   → Calls BookingService.createBooking(userId, eventId)
   
   Service does ALL validation:
   ✅ User exists? (query database)
   ✅ Event exists? (query database)
   ✅ Seats available? (check available_seats > 0)
   ✅ User not already booked? (query bookings)
   
   If all pass:
   → Create Booking object
   → BookingRepository.save()
   → MySQL: INSERT INTO bookings ...
   → Update Event: available_seats = available_seats - 1
   → EventRepository.save()
   → MySQL: UPDATE events SET available_seats = ?
   → Redirect to /student/my-bookings ✅

3. User checks bookings → GET /student/my-bookings
   → StudentController.getMyBookings()
   → BookingService.getByUser(user)
   → BookingRepository.findByUser(user)
   → MySQL: SELECT * FROM bookings WHERE user_id = ?
   → Shows user's bookings ✅
```

---

## ✅ DAY 2 CHECKPOINT

Before you practice, verify:
- [ ] Understand what each layer does (Model, Repository, Service, Controller)
- [ ] Know the 3 complete flows (Registration, Login, Booking)
- [ ] Know what breaks if each file deleted
- [ ] Can trace: Browser click → URL → Controller → Service → Repository → Database

---

---

# 🚀 QUICK REFERENCE TABLE

## All Files - Summary

| File | What | Why | If Deleted |
|------|------|-----|-----------|
| **pom.xml** | Maven config & dependencies | Build tool needs this | ❌ Won't build |
| **application.properties** | Database connection settings | App needs to connect to MySQL | ❌ Won't start |
| **EventManagementApplication.java** | Entry point (main method) | Java needs to start somewhere | ❌ Nothing runs |
| **User.java** | User entity/model | Represent users in code | ❌ Login breaks |
| **Event.java** | Event entity/model | Represent events in code | ❌ Events break |
| **Booking.java** | Booking entity/model | Links user to event | ❌ Bookings break |
| **Rating.java** | Rating entity/model | Store reviews | ⚠️ Rating feature breaks |
| **UserRepository.java** | Query users from database | Access user data | ❌ Can't find users |
| **EventRepository.java** | Query events from database | Access event data | ❌ Can't find events |
| **BookingRepository.java** | Query bookings from database | Access booking data | ❌ Can't find bookings |
| **UserService.java** | Login/registration logic | Business logic for users | ❌ Login breaks |
| **EventService.java** | Event management logic | Business logic for events | ❌ Events break |
| **BookingService.java** | Booking logic & validation | Validates all bookings | ❌ Booking breaks |
| **AuthController.java** | /login and /register routes | HTTP endpoints for auth | ❌ No login |
| **StudentController.java** | /student/* routes | HTTP endpoints for students | ❌ Students can't use app |
| **AdminController.java** | /admin/* routes | HTTP endpoints for admins | ❌ Admins can't use app |
| **AuthInterceptor.java** | Check every request for login | Security & authentication | ❌ Security broken |
| **login.html** | Login page HTML | UI for users | ⚠️ No form interface |
| **events.html** | Events list HTML | UI for browsing events | ⚠️ Can't see events |

---

# 📝 STUDY TIPS

1. **Follow the flow**: Don't jump around
   - Day 1: Models (understand data structure)
   - Day 2: Layers (how they connect)

2. **Run the app**: See it actually work while studying

3. **Trace manually**: Pick one action (like "book event")
   - Follow: Browser → Controller → Service → Repository → Database

4. **Understand relationships**:
   - User ←(many)← Booking (many)→ Event
   - User → Rating → Event

5. **Remember: If [file] deleted → [feature] breaks**

---

# 🎯 AFTER 2 DAYS

You'll know:
- ✅ How entire system works
- ✅ What each file does
- ✅ How layers interact
- ✅ Can trace complete flow
- ✅ Can read and modify code
- ✅ Can add new features

**Next step**: Start coding! Modify existing features or add new ones.

---

**Start Day 1, Hour 1 NOW!** ⚡
