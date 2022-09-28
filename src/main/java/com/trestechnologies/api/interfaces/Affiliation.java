package com.trestechnologies.api.interfaces;

public enum Affiliation {
  TRES(1), SIGNATURE(2), AMEX(6), ENSEMBLE_CANADA(7), ENSEMBLE_US(8), MART(9),
  MAST(10), TRAVEL_LEADERS(12), VIRTUOSO(13), WESTA(14), TRAVEL_SAVERS_US(15),
  TRAVEL_SAVERS_CANADA(16), PAST_SIGNATURE(17), PAST_ENSEMBLE_US(18),
  PAST_MAST(19), PAST_TRAVEL_LEADERS(20), PAST_VIRTUOSO(21), PAST_WESTA(22);
  
  public final short id;

  Affiliation ( int id ) { this.id = (short) id; }
}
