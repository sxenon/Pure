/*
 * Copyright (c) 2017  sxenon
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sxenon.pure.core.validator;

import android.widget.TextView;

import rx.functions.Action0;

/**
 * To create {@link Rule}s
 * Created by Sui on 2017/2/19.
 */

public class RulesFactory {
    public static Rule createCharSequenceMaxLengthRule(final CharSequence charSequence, final int maxLength, final Action0 onFail){
        return new Rule() {
            @Override
            public boolean isValid() {
                return charSequence.length()<=maxLength;
            }

            @Override
            public void onFail() {
                if (onFail!=null){
                    onFail.call();
                }
            }
        };
    }

    public static Rule createCharSequenceMinLengthRule(final CharSequence charSequence, final int minLength, final Action0 onFail){
        return new Rule() {
            @Override
            public boolean isValid() {
                return charSequence.length()>=minLength;
            }

            @Override
            public void onFail() {
                if (onFail!=null){
                    onFail.call();
                }
            }
        };
    }


    public static Rule createStringRegexRule(final String s, final String regex, final Action0 onFail){
        return new Rule() {
            @Override
            public boolean isValid() {
                return s.matches(regex);
            }

            @Override
            public void onFail() {
                if (onFail!=null){
                    onFail.call();
                }
            }
        };
    }
    
    public static Rule createTextMaxLengthRule(final TextView textView, final int maxLength, final Action0 onFail){
        return createCharSequenceMaxLengthRule(textView.getText(),maxLength,onFail);
    }

    public static Rule createTextMinLengthRule(final TextView textView, final int minLength, final Action0 onFail){
        return createCharSequenceMinLengthRule(textView.getText(),minLength,onFail);
    }


    public static Rule createTextRegexRule(final TextView textView, final String regex, final Action0 onFail){
        return createStringRegexRule(textView.getText().toString().trim(),regex,onFail);
    }
}
