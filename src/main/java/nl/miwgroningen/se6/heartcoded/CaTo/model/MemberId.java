package nl.miwgroningen.se6.heartcoded.CaTo.model;

import java.io.Serializable;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 *
 * is a model to make sure the groupHasUsers works together with User and Circle
 */

public class MemberId implements Serializable {
    int user;
    int circle;
}
