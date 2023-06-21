/**
 * @Author: Zuzanna Gez
 */

package com.example.joinery.enums;

import com.example.joinery.models.ServiceOrder;

/**
 * An enumerated type representing the status of an order.
 * The available statuses are "planned", "in_progress", "canceled", and "completed".
 *
 * @see ServiceOrder
 */
public enum Status{
    planned,
    in_progress,
    canceled,
    completed
}
