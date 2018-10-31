package com.namvn.shopping.pagination;


import org.hibernate.ScrollMode;
import org.hibernate.query.Query;
import org.hibernate.ScrollableResults;


import java.util.ArrayList;
import java.util.List;

public class PagingResult<E> {
    private List<E> list;
    private int totalPage;
    private List navPages;

    public PagingResult(Query<E> query, int page, int limit) {
        int totalRecords = 0;
        int pageIndex = page - 1 < 0 ? 0 : page - 1;
        int firstIndexPage = pageIndex * limit;
        // int maxIndexPage = firstIndexPage + limit;
        if (query != null) {
            ScrollableResults scrollableResults = query.scroll();
            scrollableResults.last();
            totalRecords = scrollableResults.getRowNumber() + 1;
            scrollableResults.close();

            if (firstIndexPage < totalRecords) {
                int totalPage = totalRecords / limit;
                int excessingRecord = totalRecords % limit;
                if (excessingRecord != 0) {
                    this.totalPage = totalPage + 1;
                    query.setFirstResult(firstIndexPage);
                    query.setMaxResults(limit);
                    list = query.getResultList();
                } else {
                    this.totalPage = totalPage;
                    query.setFirstResult(firstIndexPage);
                    query.setMaxResults(limit);
                    list = query.getResultList();
                }
                calculateNavigationPage(pageIndex);
            }
        }
    }

    /**
     * Ex:
     *
     * @param curentPage =23
     */
    private void calculateNavigationPage(int curentPage) {
        List list = new ArrayList();
        if (curentPage < 4) {
            if (totalPage <= 8) {
                for (int i = 1; i <= totalPage; i++)
                    list.add(i);
            } else {
                for (int i = 1; i <= 7; i++)
                    list.add(i);
                list.add(-1);
                list.add(totalPage);
            }

        } else if (curentPage == totalPage) {
            for (int i = curentPage - 7; i < curentPage; i++)
                list.add(i);
        } else if (curentPage < totalPage) {
            int minusPage = totalPage - curentPage;
            if (minusPage > 3) {
                for (int i = curentPage - 3; i < curentPage; i++)
                    list.add(i);
                for (int i = curentPage; i <= curentPage + 3; i++)
                    list.add(i);
                list.add(-1);
                list.add(totalPage);
            } else {
                for (int i = curentPage - 3; i < curentPage; i++)
                    list.add(i);
                for (int i = curentPage; i <= totalPage; i++)
                    list.add(i);
            }
        }
        navPages =list;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List getNavPages() {
        return navPages;
    }

    public void setNavPages(List navPages) {
        this.navPages = navPages;
    }

    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list = list;
    }
}
