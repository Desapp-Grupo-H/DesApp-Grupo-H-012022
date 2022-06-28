package ar.edu.unq.desapp.grupoh.backenddesappapi;

import ar.edu.unq.desapp.grupoh.backenddesappapi.webservice.aspects.LogExecutionTime;
import ar.edu.unq.desapp.grupoh.backenddesappapi.webservice.aspects.LogExecutionTimeAspectAnnotation;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;

import static org.junit.Assert.assertTrue;
import static org.reflections.ReflectionUtils.*;

public class ArchitectureTests {

    private Logger logger = LoggerFactory.getLogger(LogExecutionTimeAspectAnnotation.class);

    @Test
    public void testAllPublicMethodsInServicesAreTransactional(){
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage("ar.edu.unq.desapp.grupoh.backenddesappapi"))
                .setScanners(new SubTypesScanner(),
                        new TypeAnnotationsScanner(),
                        new MethodAnnotationsScanner()));
        Set<Class<?>> services = reflections.getTypesAnnotatedWith(Service.class);

        for (Class<?> service : services) {
            Set<Method> transactionalMethods = getAllMethods(service, withAnnotation(Transactional.class));
            Set<Method> publicMethods = getAllMethods(service, withModifier(Modifier.PUBLIC));

            publicMethods.removeIf(n -> (Modifier.isAbstract(n.getModifiers())));
            transactionalMethods.removeIf(n -> (Modifier.isAbstract(n.getModifiers())));

            assertTrue(transactionalMethods.containsAll(publicMethods));
        }
    }

    @Test
    public void testAllPublicMethodsInControllerAreLogged(){
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage("ar.edu.unq.desapp.grupoh.backenddesappapi"))
                .setScanners(new SubTypesScanner(),
                        new TypeAnnotationsScanner(),
                        new MethodAnnotationsScanner()));
        Set<Class<?>> controllers = reflections.getTypesAnnotatedWith(RestController.class);

        for (Class<?> controller : controllers) {
            Set<Method> publicMethods = getAllMethods(controller, withModifier(Modifier.PUBLIC));
            Set<Method> loggedMethods = getAllMethods(controller, withAnnotation(LogExecutionTime.class));
            logger.info("/////// transactionalMethods");
            logger.info(String.valueOf(publicMethods));
            logger.info("/////// loggedMethods");
            logger.info(String.valueOf(loggedMethods));

            loggedMethods.removeIf(n -> (Modifier.isAbstract(n.getModifiers())));

            assertTrue(publicMethods.containsAll(loggedMethods));
        }
    }
}
