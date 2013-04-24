/* 
 * Copyright Marc Guillemot
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cyberneko.html.xercesbridge;

import org.apache.xerces.impl.Version;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.XMLDocumentHandler;
import org.apache.xerces.xni.XMLLocator;
import org.apache.xerces.xni.parser.XMLDocumentFilter;
import org.apache.xerces.xni.parser.XMLDocumentSource;

/**
 * Xerces bridge for use with Xerces 2.3 and higher
 * @author Marc Guillemot
 */
public class XercesBridge {
    static private final XercesBridge instance = XercesBridge.makeInstance();

    /**
	 * Should fail for Xerces version less than 2.3 
	 * @throws InstantiationException if instantiation failed 
	 */
	public XercesBridge() throws InstantiationException {
        try {
        	getVersion();
        }
        catch (final Throwable e1) {
            throw new InstantiationException(e1.getMessage());
        }
        try {
        	final Class[] args = {String.class, String.class};
        	NamespaceContext.class.getMethod("declarePrefix", args);
        }
        catch (final NoSuchMethodException e) {
            // means that we're not using Xerces 2.3 or higher
            throw new InstantiationException(e.getMessage());
        }
	}

    /**
     * The access point for the bridge.
     * @return the instance corresponding to the Xerces version being currently used.
     */
    public static XercesBridge getInstance()
    {
        return instance;
    }

    private static XercesBridge makeInstance()
    {

        if (newInstanceOrNull(XercesBridge.class.getName()) == null) {
            throw new IllegalStateException("Failed to create XercesBridge instance");
        }
        try {
            return (XercesBridge) Class.forName(XercesBridge.class.getName()).newInstance();
        } catch (ClassNotFoundException ex) {
        } catch (SecurityException ex) {
        } catch (LinkageError ex) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        } catch (InstantiationException e) {
        }

        return null;
    }

    private static XercesBridge newInstanceOrNull(final String className) {
        try {
            return (XercesBridge) Class.forName(className).newInstance();
        }
        catch (ClassNotFoundException ex) { }
        catch (SecurityException ex) { }
        catch (LinkageError ex) { }
        catch (IllegalArgumentException e) { }
        catch (IllegalAccessException e) { }
        catch (InstantiationException e) { }

        return null;
    }

    public void NamespaceContext_declarePrefix(final NamespaceContext namespaceContext,
			final String ns, String avalue) {
        namespaceContext.declarePrefix(ns, avalue);
	}

    public String getVersion() {
        return Version.getVersion();
    }

    public void XMLDocumentHandler_startPrefixMapping(
            XMLDocumentHandler documentHandler, String prefix, String uri,
            Augmentations augs) {
        // does nothing, not needed
    }

    public void XMLDocumentHandler_startDocument(XMLDocumentHandler documentHandler, XMLLocator locator,
            String encoding, NamespaceContext nscontext, Augmentations augs) {
        documentHandler.startDocument(locator, encoding, nscontext, augs);
}

    public void XMLDocumentFilter_setDocumentSource(XMLDocumentFilter filter,
            XMLDocumentSource lastSource) {
        filter.setDocumentSource(lastSource);
    }

    /**
     * Calls endPrefixMapping on the {@link org.apache.xerces.xni.XMLDocumentHandler}.
     */
    public void XMLDocumentHandler_endPrefixMapping(
            XMLDocumentHandler documentHandler, String prefix,
            Augmentations augs) {
        // default does nothing
    }
}
