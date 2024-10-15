package com.vladsch.flexmark.ast;

public class OrderedListItem extends ListItem {
  public OrderedListItem() {}

  @Override
  public boolean isOrderedItem() {
    return true;
  }
}
