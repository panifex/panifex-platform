/*******************************************************************************
 * Panifex platform
 * Copyright (C) 2013  Mario Krizmanic
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 ******************************************************************************/
package org.panifex.module.api.accounts;

/**
 * Thrown during the change password process when the system determines the account
 * has not been expired and the user can not change expired password.
 *
 * @see {@link SecurityService#updateAccountExpiredPassword(String, String, String)}
 * 
 * @since 1.0
 */
public class AccountNotExpiredException extends Exception {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -5110932631541936126L;

    /**
     * Constructs a new AccountNotExpiredException.
     */
    public AccountNotExpiredException() {
        super();
    }
    
    /**
     * Constructs a new AccountNotExpiredException.
     *
     * @param message the reason for the exception
     */
    public AccountNotExpiredException(String message) {
        super(message);
    }

    /**
     * Constructs a new AccountNotExpiredException.
     *
     * @param cause the underlying Throwable that caused this exception to be thrown.
     */
    public AccountNotExpiredException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new AccountNotExpiredException.
     *
     * @param message the reason for the exception
     * @param cause the underlying Throwable that caused this exception to be thrown.
     */
    public AccountNotExpiredException(String message, Throwable cause) {
        super(message, cause);
    }
}
