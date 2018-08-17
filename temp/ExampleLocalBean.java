package msg.ejb;


import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class ExampleLocalBean {

    @EJB
    private ExampleNoAnnotation exampleNoAnnotation;

    private Logger logger = Logger.getLogger(ExampleLocalBean.class.getName());


    public String doSmthElse(){
        return exampleNoAnnotation.doSmth() + " \n I also did something. ";
    }

    @PostConstruct
    public void f(){
        logger.log(Level.INFO,"I also did something");
    }

}
