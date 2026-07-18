package com.github.argon.sos.planttree.util;

import com.github.argon.sos.planttree.log.Logger;
import com.github.argon.sos.planttree.log.Loggers;
import settlement.job.Job;
import settlement.job.JobClears;
import settlement.main.SETT;
import view.tool.PLACABLE;

import java.lang.reflect.Field;
import java.util.Arrays;

public class JobUtil {
    private final static Logger log = Loggers.getLogger(JobUtil.class);

    /**
     * Appends a job's placer to the settlement "clears" menu tool list.
     *
     * <p>The last slot holds a live vanilla placer (caveFill), and {@link JobClears#placers}
     * is shared by reference with {@code JOBS.clears}, so we grow-and-append rather than
     * overwrite. The field is {@code final}, hence the reflective set.</p>
     */
    public static void addClearsJob(Job job) {
        JobClears clears = SETT.JOBS().clearss;
        PLACABLE placer = job.placer();
        PLACABLE[] current = clears.placers;

        // addOnInit re-runs on save reload; skip if already appended to avoid unbounded growth.
        Class<?> placerType = placer.getClass();
        for (PLACABLE existing : current) {
            if (existing != null && existing.getClass() == placerType) {
                log.debug("Clears placer '%s' already registered, skipping", placerType.getSimpleName());
                return;
            }
        }

        PLACABLE[] grown = Arrays.copyOf(current, current.length + 1);
        grown[current.length] = placer;

        try {
            Field field = JobClears.class.getDeclaredField("placers");
            field.setAccessible(true);
            field.set(clears, grown);
        } catch (ReflectiveOperationException e) {
            log.error("Failed to append clears placer for '%s'", job.getClass().getSimpleName(), e);
            return;
        }

        log.debug("Appended '%s' to clears placers (size %d -> %d)",
                job.getClass().getSimpleName(), current.length, grown.length);
    }
}
