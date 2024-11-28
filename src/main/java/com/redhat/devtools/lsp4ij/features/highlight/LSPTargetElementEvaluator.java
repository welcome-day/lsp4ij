/*******************************************************************************
 * Copyright (c) 2024 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 * Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package com.redhat.devtools.lsp4ij.features.highlight;

import com.intellij.codeInsight.TargetElementEvaluatorEx2;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.redhat.devtools.lsp4ij.LSPIJUtils;
import com.redhat.devtools.lsp4ij.LanguageServersRegistry;
import com.redhat.devtools.lsp4ij.client.ProjectIndexingManager;
import com.redhat.devtools.lsp4ij.features.LSPPsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Finds the name identifier at the specified coordinates as the target PSI element.
 */
public class LSPTargetElementEvaluator extends TargetElementEvaluatorEx2 {
    @Override
    public @Nullable PsiElement adjustReferenceOrReferencedElement(@NotNull PsiFile file, @NotNull Editor editor, int offset, int flags, @Nullable PsiElement refElement) {
        if (!LanguageServersRegistry.getInstance().isFileSupported(file)) {
            return null;
        }

        if (ProjectIndexingManager.isIndexingAll()) {
            return null;
        }

        // Try to find the token at the caret and return fake PSI element for it
        TextRange tokenTextRange = LSPIJUtils.getTokenTextRange(file, editor, offset);
        return tokenTextRange != null ? new LSPPsiElement(file, tokenTextRange) : null;
    }
}
