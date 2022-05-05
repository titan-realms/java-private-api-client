package net.titanrealms.api.client.model.spring;

public record Pageable(int page, int size) {

    public static Pageable of(int page, int size) {
        return new Pageable(page, size);
    }

    public Pageable previous() {
        if (this.page == 0)
            throw new IllegalStateException("Cannot get previous page as current page is 0");

        return new Pageable(this.size, this.page - 1);
    }

    public Pageable next() {
        return new Pageable(this.size, this.page + 1);
    }
}
