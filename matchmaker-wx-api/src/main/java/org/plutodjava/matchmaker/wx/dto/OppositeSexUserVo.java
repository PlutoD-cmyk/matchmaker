package org.plutodjava.matchmaker.wx.dto;

import lombok.Data;
import org.plutodjava.matchmaker.db.domain.TbUser;

@Data
public class OppositeSexUserVo extends TbUser {
    private Boolean ifApply;

    private Boolean isHand;

    private String intention;
}
