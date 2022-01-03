package com.teamfresh.voc.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class VOCSimple {

    private Long vocId;
    private VOC.responsibility responsibility;
    private boolean conclude;

    @Builder
    public VOCSimple(Long vocId,VOC.responsibility responsibility,boolean conclude){
        this.vocId = vocId;
        this. responsibility = responsibility;
        this.conclude = conclude;
    }
}
