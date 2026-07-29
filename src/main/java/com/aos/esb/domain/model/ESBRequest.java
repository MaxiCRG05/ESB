package com.aos.esb.domain.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class ESBRequest
{
    private Header header;
    private JsonNode body;
}