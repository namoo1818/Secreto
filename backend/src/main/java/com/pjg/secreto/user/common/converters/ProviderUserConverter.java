package com.pjg.secreto.user.common.converters;

public interface ProviderUserConverter<T,R> {
    R convert(T t);
}
