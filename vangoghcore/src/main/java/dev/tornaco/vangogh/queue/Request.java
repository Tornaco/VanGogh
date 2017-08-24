package dev.tornaco.vangogh.queue;

import dev.tornaco.vangogh.media.ImageSource;

/**
 * Created by guohao4 on 2017/8/24.
 * Email: Tornaco@163.com
 */
public class Request {
    private String alias;
    private long requestTimeMills;
    private ImageSource imageSource;

    Request(String alias, long requestTimeMills, ImageSource imageSource) {
        this.alias = alias;
        this.requestTimeMills = requestTimeMills;
        this.imageSource = imageSource;
    }

    public static RequestBuilder builder() {
        return new RequestBuilder();
    }

    public String getAlias() {
        return this.alias;
    }

    public long getRequestTimeMills() {
        return this.requestTimeMills;
    }

    public ImageSource getImageSource() {
        return this.imageSource;
    }

    public String toString() {
        return "dev.tornaco.vangogh.queue.Request(alias=" + this.getAlias() + ", requestTimeMills=" + this.getRequestTimeMills() + ", imageSource=" + this.getImageSource() + ")";
    }

    public static class RequestBuilder {
        private String alias;
        private long requestTimeMills;
        private ImageSource imageSource;

        RequestBuilder() {
        }

        public Request.RequestBuilder alias(String alias) {
            this.alias = alias;
            return this;
        }

        public Request.RequestBuilder requestTimeMills(long requestTimeMills) {
            this.requestTimeMills = requestTimeMills;
            return this;
        }

        public Request.RequestBuilder imageSource(ImageSource imageSource) {
            this.imageSource = imageSource;
            return this;
        }

        public Request build() {
            return new Request(alias, requestTimeMills, imageSource);
        }

        public String toString() {
            return "dev.tornaco.vangogh.queue.Request.RequestBuilder(alias=" + this.alias + ", requestTimeMills=" + this.requestTimeMills + ", imageSource=" + this.imageSource + ")";
        }
    }
}
