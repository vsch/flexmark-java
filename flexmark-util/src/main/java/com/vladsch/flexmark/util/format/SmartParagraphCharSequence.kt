///*
// * Copyright (c) 2015-2019 Vladimir Schneider <vladimir.schneider@gmail.com>
// *
// * Licensed to the Apache Software Foundation (ASF) under one
// * or more contributor license agreements.  See the NOTICE file
// * distributed with this work for additional information
// * regarding copyright ownership.  The ASF licenses this file
// * to you under the Apache License, Version 2.0 (the
// * "License"); you may not use this file except in compliance
// * with the License.  You may obtain a copy of the License at
// *
// *   http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing,
// * software distributed under the License is distributed on an
// * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// * KIND, either express or implied.  See the License for the
// * specific language governing permissions and limitations
// * under the License.
// */
//
//package com.vladsch.flexmark.util.format
//
//import java.util.*
//import java.util.function.Supplier
//
//class SmartCharArraySequence {
//
//}
//
//class SmartParagraphCharSequence {
//
//    // TODO: need a SmartDependentVersionHolder to SmartVersionedDataHolder adapter class so that smar sequences can be included
//    // in list of dependents of properties
//
//
//
//
//    override val length: Int get() = resultSequence.length
//    override fun getCharsImpl(dst: CharArray, dstOffset: Int) = resultSequence.getChars(dst, dstOffset)
//    override fun charAtImpl(index: Int): Char = resultSequence[index]
//    override fun properSubSequence(startIndex: Int, endIndex: Int): SmartCharSequence = resultSequence.subSequence(startIndex, endIndex)
//    override fun getCharsImpl(): CharArray = resultSequence.chars
//    override fun getCachedProxy(): SmartCharSequence = resultSequence.cachedProxy
//
//    override fun trackedSourceLocation(index: Int): TrackedLocation {
//        checkIndex(index)
//        val location = resultSequence.trackedSourceLocation(index)
//        return adjustTrackedSourceLocation(location)
//    }
//
//    protected fun adjustTrackedLocation(location: TrackedLocation?): TrackedLocation? {
//        // TODO: adjust tracking location as needed
//        //        if (location != null) {
//        //            val leadPadding = myLeftPadding.length + myPrefix.value.length
//        //            if (leadPadding > 0) {
//        //                return adjustTrackedSourceLocation(location)
//        //            }
//        //        }
//        return location
//    }
//
//    protected fun adjustTrackedSourceLocation(location: TrackedLocation): TrackedLocation {
//        // TODO: adjust tracking location as needed
//        //        val leadPadding = myLeftPadding.length + myPrefix.value.length
//        //        if (leadPadding > 0) {
//        //            return location.withIndex(leadPadding + location.index).withPrevClosest(leadPadding + location.prevIndex).withNextClosest(leadPadding + location.nextIndex)
//        //        }
//        return location
//    }
//
//    override fun getMarkers(id: String?): List<TrackedLocation> {
//        val locations = myReplacedChars.getMarkers(id)
//        if (!locations.isEmpty()) {
//            // re-map
//            val markers = ArrayList(locations)
//            for (location in locations) {
//                markers.add(adjustTrackedLocation(location))
//            }
//            if (!markers.isEmpty()) return markers
//        }
//        return TrackedLocation.EMPTY_LIST
//    }
//}
//
