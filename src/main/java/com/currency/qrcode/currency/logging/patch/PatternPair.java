/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.currency.qrcode.currency.logging.patch;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 用于日志 pattern 替换.
 *
 * @author liucunliang
 * @version 1.0.0
 * @since 1.0.0
 * @create 2021/1/11 下午3:57
 */
public class PatternPair {
    public final Pattern pattern;
    public final Pattern patternR2;
    public final String replacement;

    public PatternPair(Pattern pattern, Pattern patternR2, String replacement) {
        this.pattern = pattern;
        this.patternR2 = patternR2;
        this.replacement = replacement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PatternPair that = (PatternPair) o;
        return Objects.equals(pattern, that.pattern) && Objects.equals(patternR2, that.patternR2) &&
                   Objects.equals(replacement, that.replacement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pattern, patternR2, replacement);
    }
}
