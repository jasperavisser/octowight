package nl.haploid.octowight.sample.configuration

import org.springframework.context.annotation.{ComponentScan, Configuration, FilterType}

@Configuration
@ComponentScan(basePackages = Array("nl.haploid.octowight"), excludeFilters = Array(
  new ComponentScan.Filter(`type` = FilterType.REGEX, pattern = Array("nl.haploid.octowight.registry.*"))
))
class TestConfiguration
