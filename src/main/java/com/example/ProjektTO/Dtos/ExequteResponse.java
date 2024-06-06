package com.example.ProjektTO.Dtos;

import java.util.List;
import java.util.Map;

public record ExequteResponse(Boolean status, List<Map<String,String>>rows) {
}
