/*
 *  Copyright (c) 2019, Oracle and/or its affiliates. All rights reserved.
 *  DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 *  This code is free software; you can redistribute it and/or modify it
 *  under the terms of the GNU General Public License version 2 only, as
 *  published by the Free Software Foundation.  Oracle designates this
 *  particular file as subject to the "Classpath" exception as provided
 *  by Oracle in the LICENSE file that accompanied this code.
 *
 *  This code is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 *  FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 *  version 2 for more details (a copy is included in the LICENSE file that
 *  accompanied this code).
 *
 *  You should have received a copy of the GNU General Public License version
 *  2 along with this work; if not, write to the Free Software Foundation,
 *  Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 *   Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 *  or visit www.oracle.com if you need additional information or have any
 *  questions.
 *
 */
package jdk.incubator.foreign;

import java.lang.constant.ConstantDescs;
import java.lang.constant.DynamicConstantDesc;
import java.util.Optional;
import java.util.OptionalLong;

/**
 * A padding layout. A padding layout specifies the size of extra space which is typically not accessed by applications,
 * and is typically used for aligning member layouts around word boundaries.
 * <p>
 * This is a <a href="{@docRoot}/java.base/java/lang/doc-files/ValueBased.html">value-based</a>
 * class; use of identity-sensitive operations (including reference equality
 * ({@code ==}), identity hash code, or synchronization) on instances of
 * {@code PaddingLayout} may have unpredictable results and should be avoided.
 * The {@code equals} method should be used for comparisons.
 *
 * @implSpec
 * This class is immutable and thread-safe.
 */
/* package-private */ class PaddingLayout extends AbstractLayout implements MemoryLayout {

    PaddingLayout(long size) {
        this(size, size, Optional.empty());
    }

    PaddingLayout(long size, long alignment, Optional<String> name) {
        super(size, alignment, name);
    }

    @Override
    public long bitSize() {
        return size;
    }

    @Override
    public String toString() {
        return decorateLayoutString("x" + size);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!super.equals(other)) {
            return false;
        }
        if (!(other instanceof PaddingLayout)) {
            return false;
        }
        PaddingLayout p = (PaddingLayout)other;
        return size == p.size;
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ Long.hashCode(size);
    }

    @Override
    PaddingLayout dup(long alignment, Optional<String> name) {
        return new PaddingLayout(size, alignment, name);
    }

    @Override
    public Optional<DynamicConstantDesc<MemoryLayout>> describeConstable() {
        return Optional.of(DynamicConstantDesc.ofNamed(ConstantDescs.BSM_INVOKE, "padding",
                CD_LAYOUT, MH_PADDING, size));
    }

    //hack: the declarations below are to make javadoc happy; we could have used generics in AbstractLayout
    //but that causes issues with javadoc, see JDK-8224052

    /**
     * {@inheritDoc}
     */
    @Override
    public PaddingLayout withName(String name) {
        return (PaddingLayout)super.withName(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PaddingLayout withBitAlignment(long alignmentBits) throws IllegalArgumentException {
        return (PaddingLayout)super.withBitAlignment(alignmentBits);
    }
}
