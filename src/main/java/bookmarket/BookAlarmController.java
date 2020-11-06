package bookmarket;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
 public class BookAlarmController {

  @Autowired
  BookAlarmRepository bookAlarmRepository;


 @RequestMapping(method = RequestMethod.GET, path = "/autoScaleTest") //localhost:8080/api/getMethod
 public String autoScaleTest(@RequestParam long id) throws InterruptedException {

    System.out.println("========= bookAlarm AutoScale Test ============");
    Optional<BookAlarm> bookAlarm = bookAlarmRepository.findById(id);

    //Sleep for Load
    Thread.sleep(3000);

    return "";

  }




  @GetMapping("/selectBookAlarmInfo")
  @HystrixCommand(fallbackMethod = "fallback", commandProperties = {
          @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
          @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000")
  })
  public String selectBookAlarmInfo(@RequestParam long id) throws InterruptedException {

   if (id <= 0) {
    System.out.println("@@@ CircuitBreaker!!!");
    Thread.sleep(10000);
    //throw new RuntimeException("CircuitBreaker!!!");
   } else {
    Optional<BookAlarm> bookAlarm = bookAlarmRepository.findById(id);
    return bookAlarm.get().toString();



   }

   System.out.println("$$$ SUCCESS!!!");
   return " SUCCESS!!!";
  }

  private String fallback(long id) {
   System.out.println("### fallback!!!");
   return "CircuitBreaker!!!";
  }
 }
