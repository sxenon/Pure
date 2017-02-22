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

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;

/**
 * To validate {@link Rule}s
 * Created by Sui on 2017/2/19.
 */

public class Validator {
    private List<Rule> ruleList;

    public Validator() {
       this(8);
    }

    public Validator(int ruleCount) {
        ruleList=new ArrayList<>(ruleCount);
    }

    public Validator addRule(Rule rule){
        ruleList.add(rule);
        return this;
    }

    public void validate(final Action0 onSuccess) {
        Observable.from(ruleList).subscribe(new Subscriber<Rule>() {
            @Override
            public void onCompleted() {
                onSuccess.call();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Rule rule) {
                if (!rule.isValid()){
                    rule.onFail();
                    unsubscribe();
                }
            }
        });
    }



}
