package net.titanrealms.api.client.model.spring;

import java.util.List;

public record Page<T>(List<T> content, boolean last, boolean first, long totalPages, long totalElements,
                      int size, int number) {
}
