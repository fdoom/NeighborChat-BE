package kit.hackathon.nearbysns.global.config.geometry;

import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class GeometryConfig {
    @Bean
    public GeometryFactory geometryFactory() {
        return new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING), 4326);
    }
}
