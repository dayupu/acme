package com.manage.base.act;

/**
 * Created by bert on 2017/10/4.
 */
public class ActBusiness {

    private Long id;
    private ActSource source;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ActSource getSource() {
        return source;
    }

    public void setSource(String source) {
        for (ActSource actSource : ActSource.values()) {
            if (actSource.getKey().equals(source)) {
                this.source = actSource;
                break;
            }
        }
    }

    public void setSource(ActSource source) {
        this.source = source;
    }
}
