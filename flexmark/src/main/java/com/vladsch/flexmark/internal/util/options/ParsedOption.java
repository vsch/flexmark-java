/*
 * Copyright (c) 2015-2016 Vladimir Schneider <vladimir.schneider@gmail.com>, all rights reserved.
 *
 * This code is private property of the copyright holder and cannot be used without
 * having obtained a license or prior written permission of the of the copyright holder.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

package com.vladsch.flexmark.internal.util.options;

import com.vladsch.flexmark.internal.util.sequence.BasedSequence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParsedOption<T> {
    final protected BasedSequence mySource;
    final protected OptionParser<T> myOptionParser;
    final protected ParsedOptionStatus myOptionResult;
    final protected List<ParserMessage> myMessages;

    public ParsedOption(BasedSequence source, OptionParser<T> optionParser, ParsedOptionStatus optionResult) {
        this(source, optionParser, optionResult, (List<ParserMessage>) null);
    }

    public ParsedOption(BasedSequence source, OptionParser<T> optionParser, ParsedOptionStatus optionResult, ParserMessage message) {
        this(source, optionParser, optionResult, Collections.singletonList(message));
    }

    public ParsedOption(BasedSequence source, OptionParser<T> optionParser, ParsedOptionStatus optionResult, List<ParserMessage> messages) {
        this(source, optionParser, optionResult, messages, null);
    }

    public ParsedOption(BasedSequence source, OptionParser<T> optionParser, ParsedOptionStatus optionResult, List<ParserMessage> messages, List<ParsedOption<T>> parsedOptions) {
        mySource = source;

        if (parsedOptions != null) {
            ArrayList<ParserMessage> mergedMessages = messages != null ? new ArrayList<>(messages) : null;

            for (ParsedOption<T> parsedOption : parsedOptions) {
                optionResult = optionResult.escalate(parsedOption.getOptionResult());
                if (parsedOption.getMessages() != null) {
                    if (mergedMessages == null) mergedMessages = new ArrayList<>();
                    mergedMessages.addAll(parsedOption.getMessages());
                }
            }
            messages = mergedMessages;
        }

        myOptionParser = optionParser;
        myOptionResult = optionResult;
        myMessages = messages;
    }

    public BasedSequence getSource() {
        return mySource;
    }

    public OptionParser<T> getOptionParser() {
        return myOptionParser;
    }

    public ParsedOptionStatus getOptionResult() {
        return myOptionResult;
    }

    public List<ParserMessage> getMessages() {
        return myMessages;
    }
}
