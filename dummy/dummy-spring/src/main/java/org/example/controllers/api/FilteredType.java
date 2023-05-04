package org.example.controllers.api;

public record FilteredType<T> (T value, FilterOperation filterOperation) {}
