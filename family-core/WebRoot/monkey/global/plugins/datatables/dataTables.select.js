table.dataTable tbody > tr.selected,
table.dataTable tbody > tr > .selected {
  background-color: #B0BED9;
}
table.dataTable.stripe tbody > tr.odd.selected,
table.dataTable.stripe tbody > tr.odd > .selected, table.dataTable.display tbody > tr.odd.selected,
table.dataTable.display tbody > tr.odd > .selected {
  background-color: #acbad4;
}
table.dataTable.hover tbody > tr.selected:hover,
table.dataTable.hover tbody > tr > .selected:hover, table.dataTable.display tbody > tr.selected:hover,
table.dataTable.display tbody > tr > .selected:hover {
  background-color: #aab7d1;
}
table.dataTable.order-column tbody > tr.selected > .sorting_1,
table.dataTable.order-column tbody > tr.selected > .sorting_2,
table.dataTable.order-column tbody > tr.selected > .sorting_3,
table.dataTable.order-column tbody > tr > .selected, table.dataTable.display tbody > tr.selected > .sorting_1,
table.dataTable.display tbody > tr.selected > .sorting_2,
table.dataTable.display tbody > tr.selected > .sorting_3,
table.dataTable.display tbody > tr > .selected {
  background-color: #acbad5;
}
table.dataTable.display tbody > tr.odd.selected > .sorting_1, table.dataTable.order-column.stripe tbody > tr.odd.selected > .sorting_1 {
  background-color: #a6b4cd;
}
table.dataTable.display tbody > tr.odd.selected > .sorting_2, table.dataTable.order-column.stripe tbody > tr.odd.selected > .sorting_2 {
  background-color: #a8b5cf;
}
table.dataTable.display tbody > tr.odd.selected > .sorting_3, table.dataTable.order-column.stripe tbody > tr.odd.selected > .sorting_3 {
  background-color: #a9b7d1;
}
table.dataTable.display tbody > tr.even.selected > .sorting_1, table.dataTable.order-column.stripe tbody > tr.even.selected > .sorting_1 {
  background-color: #acbad5;
}
table.dataTable.display tbody > tr.even.selected > .sorting_2, table.dataTable.order-column.stripe tbody > tr.even.selected > .sorting_2 {
  background-color: #aebcd6;
}
table.dataTable.display tbody > tr.even.selected > .sorting_3, table.dataTable.order-column.stripe tbody > tr.even.selected > .sorting_3 {
  background-color: #afbdd8;
}
table.dataTable.display tbody > tr.odd > .selected, table.dataTable.order-column.stripe tbody > tr.odd > .selected {
  background-color: #a6b4cd;
}
table.dataTable.display tbody > tr.even > .selected, table.dataTable.order-column.stripe tbody > tr.even > .selected {
  background-color: #acbad5;
}
table.dataTable.display tbody > tr.selected:hover > .sorting_1, table.dataTable.order-column.hover tbody > tr.selected:hover > .sorting_1 {
  background-color: #a2aec7;
}
table.dataTable.display tbody > tr.selected:hover > .sorting_2, table.dataTable.order-column.hover tbody > tr.selected:hover > .sorting_2 {
  background-color: #a3b0c9;
}
table.dataTable.display tbody > tr.selected:hover > .sorting_3, table.dataTable.order-column.hover tbody > tr.selected:hover > .sorting_3 {
  background-color: #a5b2cb;
}
table.dataTable.display tbody > tr:hover > .selected,
table.dataTable.display tbody > tr > .selected:hover, table.dataTable.order-column.hover tbody > tr:hover > .selected,
table.dataTable.order-column.hover tbody > tr > .selected:hover {
  background-color: #a2aec7;
}
table.dataTable td.select-checkbox {
  position: relative;
}
table.dataTable td.select-checkbox:before, table.dataTable td.select-checkbox:after {
  display: block;
  position: absolute;
  top: 1.2em;
  left: 50%;
  width: 12px;
  height: 12px;
  box-sizing: border-box;
}
table.dataTable td.select-checkbox:before {
  content: ' ';
  margin-top: -6px;
  margin-left: -6px;
  border: 1px solid black;
  border-radius: 3px;
}
table.dataTable tr.selected td.select-checkbox:after {
  content: '\2714';
  margin-top: -11px;
  margin-left: -4px;
  text-align: center;
  text-shadow: 1px 1px #B0BED9, -1px -1px #B0BED9, 1px -1px #B0BED9, -1px 1px #B0BED9;
}

div.dataTables_wrapper span.select-info,
div.dataTables_wrapper span.select-item {
  margin-left: 0.5em;
}

@media screen and (max-width: 640px) {
  div.dataTables_wrapper span.select-info,
  div.dataTables_wrapper span.select-item {
    margin-left: 0;
    display: block;
  }
}