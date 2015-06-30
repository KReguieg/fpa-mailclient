package control;

/**
 * Created by Khaled Reguieg on 30.06.2015.
 */
public abstract class Filter implements FPAMessageLoader{

    protected FPAMessageLoader fpaMessageLoader;
    protected String filterCriteria;

    public Filter(FPAMessageLoader fpaMessageLoader, String filterCriteria) {
        this.fpaMessageLoader = fpaMessageLoader;
        this.filterCriteria = filterCriteria;
    }
}
