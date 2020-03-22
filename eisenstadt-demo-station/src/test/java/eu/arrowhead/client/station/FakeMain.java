package eu.arrowhead.client.station;

import eu.arrowhead.common.CommonConstants;
import eu.arrowhead.demo.grovepi.ControllableLed;
import eu.arrowhead.demo.grovepi.mocks.FakeGrovePI;
import eu.arrowhead.demo.grovepi.mocks.FakeLed;
import eu.arrowhead.demo.web.HttpServerCustomizer;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.iot.raspberry.grovepi.GrovePi;
import org.iot.raspberry.grovepi.devices.GroveLed;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = CommonConstants.BASE_PACKAGE)
@SpringBootConfiguration
@EnableConfigurationProperties({ServerProperties.class})
public class FakeMain {

    public static void main(String[] args) {
        final ConfigurableApplicationContext ctx = SpringApplication.run(FakeMain.class);
        ChargingStationController.chargingService = ctx.getBean(ChargingStationService.class);
    }

    @Bean
    public GrovePi grovePi() throws IOException {
        return new FakeGrovePI();
    }

    @Bean
    public ExecutorService executorService() {
        return Executors.newCachedThreadPool();
    }

    @Bean("greenLed")
    public GroveLed greenLed(final GrovePi grovePi) throws IOException {
        return new FakeLed(grovePi, 0);
    }

    @Bean("redLed")
    public GroveLed redLed(final GrovePi grovePi) throws IOException {
        return new FakeLed(grovePi, 1);
    }

    @Bean("greenControl")
    public ControllableLed blinkingGreenLed(final ExecutorService executorService,
                                            @Qualifier("greenLed") final GroveLed led) {
        return new ControllableLed(executorService, led);
    }

    @Bean("redControl")
    public ControllableLed blinkingRedLed(final ExecutorService executorService,
                                          @Qualifier("redLed") final GroveLed led) {
        return new ControllableLed(executorService, led);
    }

    @Bean
    public HttpServerCustomizer httpServerCustomizer() {
        return (server) -> server.registerPackages(CommonConstants.BASE_PACKAGE);
    }
}
