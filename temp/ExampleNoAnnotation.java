package msg.ejb;


import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class ExampleNoAnnotation {

    private Logger logger = Logger.getLogger(ExampleNoAnnotation.class.getName());

    public String doSmth(){
        return "I did something";
    }

    @PostConstruct
    public void f(){
        logger.log(Level.INFO,"I did something");
    }
}
