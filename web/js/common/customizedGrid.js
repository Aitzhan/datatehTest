define([
    "gridx/Grid",
    'gridx/core/model/cache/Async',
    "gridx/modules/VirtualVScroller",
    "gridx/modules/ColumnResizer",
    "gridx/modules/extendedSelect/Row",
    "gridx/modules/SingleSort",
    "gridx/modules/Pagination",
    "gridx/modules/pagination/PaginationBar",
    "gridx/modules/Filter",
    "gridx/modules/filter/FilterBar"
], function(Grid, Cache, VirtualVScroller, ColumnResizer, SelectRow,
             SingleSort, Pagination, PaginationBar, Filter, FilterBar) {
    return {
        getGrid: function(store, structure) {
            return grid = new Grid({
                cacheClass: Cache,
                store: store,
                structure: structure,
                style: 'height: 100%',
                modules: [
                    VirtualVScroller,
                    ColumnResizer,
                    SelectRow,
                    SingleSort,
                    Pagination,
                    PaginationBar,
                    Filter,
                    FilterBar
                ],

                paginationVisibleSteppers: 5,
                paginationBarPosition: 'bottom'
            });
        }
    }
});
