package my.thereisnospoon.pheebo.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
@Aspect
public class HitCounter {

	private static final Logger log = LoggerFactory.getLogger(HitCounter.class);

	public static final AtomicLong hits = new AtomicLong(0);

	@Before("execution(* my.thereisnospoon.pheebo.controllers.HomeController.home(..))")
	public void logHit() {

		log.debug("Was hit. Current: " + (hits.get() + 1));

		hits.incrementAndGet();
	}
}
