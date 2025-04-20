package br.com.ogvieira.first.serialization.converter

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper
import org.springframework.http.MediaType

class YamlJackson2HttpMessageConverter : AbstractJackson2HttpMessageConverter(
    YAMLMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL),
    MediaType.parseMediaType("application/x-yaml")
)